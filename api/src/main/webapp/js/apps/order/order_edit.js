"use strict"

$(function () {

    var App = window.App = window.App || {};

    var template = $('#hidden-template').html();

    // var $modal = $('#modal').modal({show: false});

    App.Cargo = Backbone.Model.extend({
        defaults: {
            name: "",
            title: "",
            weight: 0,
            srcCityId: 0,
            srcCityName: "",
            dstCityId: 0,
            dstCityName: "",
        }
    });


    Handlebars.registerHelper('pluralize', function(number, singular, plural) {
        if (number === 1)
            return singular;
        else
            return (typeof plural === 'string' ? plural : singular + 's');
    });

    Handlebars.registerHelper('pluralCount', function(number, singular, plural) {
        return number+' '+Handlebars.helpers.pluralize.apply(this, arguments);
    });

    App.City = Backbone.Model.extend({
        defaults: {
            name: "",
            ordinal: 0,
        }
    });

    App.Order = Backbone.Model.extend({
        defaults: {
            cargoCollection: {},
            driverCollection: {},
            selectedTruckId : null,
            selectedDriversCollection: {},
            routeLength: null,
            requiredCapacity: null,
        },

        getMaxDrivers: function (truckCollection) {
            var truckId = this.get('selectedTruckId');
            if (truckId != null) {
                return truckCollection.findWhere({id: truckId}).get('maxDrivers')
            }
            return null;
        }
    });

    App.Truck = Backbone.Model.extend({
        defaults: {
            id: "", name: "", maxDrivers: 0, capacityTon: 0
        },
    });

    App.TruckCollection = Backbone.Collection.extend({
        model: App.Truck,
    });

    App.Driver = Backbone.Model.extend({
        defaults: {
            id: "", name: "", workedHours: 0
        },
    });

    App.DriverCollection = Backbone.Collection.extend({
        model: App.Truck,
    });


    App.CityCollection = Backbone.Collection.extend({
        model: App.City,

        // events: {
        //     update: "onUpdate"
        // },
        //
        // onUpdate: function (options) {
        //     // _.options.changes.added
        //     // city.set('ordinal', _.max([0, _.max(this.collection.pluck('ordinal'))]) + 1);
        // },
        //
        // initialize: function (collection, options) {
        //     this.cargoes = options.cargoes;
        //     _.bind(this.canBeFirstInRoute, this);
        //     _.bind(this.canBeLastInRoute, this);
        // },

        // Updates cities list base on cargoCollection
        updateCities: function (cargoCollection) {

            var oldSeqCities = this.pluck('id');

            // Loop through cargoes. For each cargo check that it exists in collection array.
            // If cargo city is not found in collection array, append it to end of collection.
            _.forEach(['src', 'dst'], function (dir) {
                cargoCollection.forEach(function (cargo) {

                    var existingCity = this.find({id: +cargo.get(dir + 'CityId')});
                    // If city from cargo collection is not present in sequence collection
                    if (existingCity == null) {
                        // Add it to collection
                        var newSeqCity = new App.City({
                            name: cargo.get(dir + 'CityName'),
                            id: +cargo.get(dir + 'CityId'),
                            ordinal: this.length,
                        });
                        this.add(newSeqCity);
                    } else {
                        // existing city
                        oldSeqCities = _.without(oldSeqCities, existingCity.get('id'));
                    }
                }, this);
            }, this);

            // Remove cities from collection that are not present in cargoes
            _.forEach(oldSeqCities, function (id) {
                this.remove(this.where({'id': id}))
            }, this);
        },


        comparator: function(model) {
            return model.get('ordinal');
        },
        // // Check that given city can be start of route
        // canBeFirstInRoute: function (city) {
        //     return this.cargoes.findWhere({ srcCityId: city.get('id') }) != null;
        // },
        // canBeLastInRoute: function (city) {
        //     return this.cargoes.findWhere({ dstCityId: city.get('id') }) != null;
        // }

    });


    App.CargoCollection = Backbone.Collection.extend({
        model: App.Cargo,
        // url: CONTEXT_PATH + '/api/order/cargoes.do'
        //
        // sync: function(method, model, options) {
        //     if (method == "read") {
        //
        //         $.ajax(this.url, {
        //             context: this,
        //             data: this.options(),
        //         }).done(function (json) {
        //             this.set('length', json.length);
        //             this.set('requiredCapacity', json.requiredCapacity);
        //             this.trucks.add(json.trucks);
        //
        //         }).fail(function (jqXhr, textStatus) {
        //             alert("Ajax error:" + jqXhr.url);
        //         })
        //     }
        // },

    });

    //
    // TABLE ROW
    //
    App.CargoRowView = Backbone.View.extend({

        tagName: "tr",
        template: Handlebars.compile($('#cargo-item-template').html()),
        model: App.Cargo,
        events: {
            "click .edit": "editClicked",
            "click .remove": "removeClicked",
        },

        initialize: function () {
            this.listenTo(this.model, "change", this.render);
            this.listenTo(this.model, "remove", this.remove);
        },

        editClicked: function () {

            var viewEdit = new EditCargoDialogView({ model: this.model });
            viewEdit.render();

            // $modal.modal("show")
            // alert("Clicked" + this.model.get("title"));
        },

        removeClicked: function () {
            if (confirm('Are you sure to delete this item?')) {
                this.model.destroy();
            }
        },

        render: function() {
            this.$el.html(this.template(this.model.attributes));
            return this;
        },
    });

    //
    // TABLE
    //
    var CargoTableView = Backbone.View.extend({
        model: App.CargoCollection,
        el: $("#cargoTable tbody"),

        initialize: function () {
            this.listenTo(this.collection, "add reset", this.render);

            var that = this;
            $(".addCargo").click(function () {
                var newCargo = new App.Cargo({title: ''});
                var cargoDialog = new EditCargoDialogView({ model: newCargo });
                that.listenTo(cargoDialog, "submitted", that.onNewCargo);
                cargoDialog.render();
            });
        },

        onNewCargo: function (cargo) {
            this.collection.add(cargo);
        },

        // Re-render the titles of the todo item.
        render: function() {

            this.$el.html("");

            for (var i = 0; i < this.collection.length; i++) {
                var rowView = new App.CargoRowView({ model: this.collection.at(i) });
                this.$el.append(rowView.$el);
                rowView.render();
            }

            // this.input = this.$('.edit');
            return this;
        },
    });

    //
    // DIALOG
    //
    var EditCargoDialogView = Backbone.View.extend({
        model: App.Cargo,
        tagName: "div",

        template: Handlebars.compile($('#edit-cargo-template').html()),
        events: {
            // 'submit': "editCargoSubmit",
        },

        initialize: function () {
            var that = this;
            this.$el.html(this.template(this.model.attributes));
            this.$modal = this.$el.find('#modal');

            this.$modal.on("hidden.bs.modal", function () {
                that.remove();
            });
            this.$modal.on('shown.bs.modal', function (e) {
                $(e).find('input[name=name]').first().focus();
            });

            this.$modal.find('form').validator().on('submit', function (e) {
                if (!e.isDefaultPrevented()) {
                    e.preventDefault();
                    that.editCargoSubmit();
                }
            })
        },

        editCargoSubmit: function () {
            var that = this;
            this.$el.find('input[name]').each(function () {
                that.model.set($(this).attr('name'), $(this).val());
            });
            this.$modal.modal("hide");
            this.trigger("submitted", this.model);
        },

        render: function () {

            $("body").prepend(this.$el);

            // TODO: easyAutocomplete is not works until we call prepend (attach to DOM), but initialization here is not right place

            this.$el.find('#srcCityName').easyAutocomplete({
                url: function(phrase) {
                    return CONTEXT_PATH + "/api/city/autocomplete.do?q=" + phrase + "&format=json";
                },
                getValue: "name",
                adjustWidth: false,
                list: {
                    match: {
                        enabled: true
                    },
                    onSelectItemEvent: function() {
                        var cityId = $("#srcCityName").getSelectedItemData().id;

                        $("#srcCityId").val(cityId);
                    },
                }
            });

            this.$el.find('#dstCityName').easyAutocomplete({
                url: function(phrase) {
                    return CONTEXT_PATH + "/api/city/autocomplete.do?q=" + phrase + "&format=json";
                },
                getValue: "name",
                adjustWidth: false,
                list: {
                    match: {
                        enabled: true
                    },
                    onSelectItemEvent: function() {
                        var cityId = $("#dstCityName").getSelectedItemData().id;

                        $("#dstCityId").val(cityId);
                    },
                },
                theme: "bootstrap"
            });

            this.$modal.modal("show");
            return this;
        }
    });

    //
    // CITY ROW VIEW
    //

    var CityView = Backbone.View.extend({
        model: App.City,
        tagName: 'li',
        className: 'panel panel-info',
        template: Handlebars.compile($("#city-li-template").html()),
        events: {
            'update' : 'updateSort',
        },

        updateSort: function(event, index) {
            this.$el.trigger('update-sort', [this.model, index]);
        },

        render: function () {
            this.$el.html(this.template(this.model.attributes));
            return this;
        }
    });

    var RouteSummaryView = Backbone.View.extend({
        model: App.Order,
        el: $(".route-summary"),
        template: Handlebars.compile($("#route-summary-template").html()),

        initialize: function () {
            this.listenTo(this.model, "change:routeLength change:requiredCapacity", this.render);
            // this.listenTo(this.collection, "remove", this.render);
        },

        render: function () {
            this.$el.html(this.template(this.model.attributes));
            return this;
        },
    });

    var RouteCityOrderView = Backbone.View.extend({
        model: App.CityCollection,
        el: $(".route-cities"),

        events: {
            'update-sort': 'updateSort',
        },


        initialize: function (options) {
            // cargo collection required to verify permutations
            this.cargoCollection = options.cargoCollection;

            this.listenTo(this.collection, "add", this.render);
            this.listenTo(this.collection, "remove", this.render);

            this.$el.sortable({
                stop: function( event, ui ) {
                    ui.item.trigger('update', ui.item.index());
                },
            });
        },

        isNewSortValid: function (model, newPosition) {

            // case 1: newModel position is on edge
            if (newPosition == 0) {
                return this.canBeFirstInRoute(model);
            }
            if (newPosition == this.collection.length - 1) {
                return this.canBeLastInRoute(model);
            }

            // case 2: newModel position is in middle. So other elements now can be on edge positions.
            var newFirst = _.first(this.collection.without(model));
            if (!this.canBeFirstInRoute(newFirst)) {
                return false;
            }

            var newLast = _.last(this.collection.without(model));
            if (!this.canBeLastInRoute(newLast)) {
                return false;
            }

            return true;
        },

        // Check that given city can be start of route
        canBeFirstInRoute: function (city) {
            return this.cargoCollection.findWhere({srcCityId: city.get('id')}) != null;
        },
        canBeLastInRoute: function (city) {
            return this.cargoCollection.findWhere({dstCityId: city.get('id')}) != null;
        },

        add: function (city) {
            var cityView = new CityView({ model: city });
            this.$el.append(cityView.render().el);
        },

        //
        // remove: function (city) {
        //     var cityView = new CityView({ model: city });
        //     this.$el.append(cityView.render().el);
        // },

        render: function () {
            this.$el.html("");

            for (var i = 0; i < this.collection.length; i++) {
                this.add(this.collection.at(i));
            }
            this.$el.sortable(this.collection.length > 2 ? "enable" : "disable");

            return this;
        },

        updateSort: function(event, model, position) {

            if (!this.isNewSortValid(model, position)) {
                this.$el.sortable("cancel");
                return;
            }

            this.collection.remove(model);

            this.collection.each(function (model, index) {
                var ordinal = index;
                if (index >= position) {
                    ordinal += 1;
                }
                model.set('ordinal', ordinal);
            });

            model.set('ordinal', position);
            this.collection.add(model, {at: position});

            // to update ordinals on server:
            // var ids = this.collection.pluck('id');
            // $('#post-data').html('post ids to server: ' + ids.join(', '));

            this.render();
        }

    });

    var TruckListView = Backbone.View.extend({
        model: App.TruckCollection,
        el: $(".truck-selector"),
        template: Handlebars.compile($("#truck-select-template").html()),

        initialize: function (options) {
            this.order = options.order;
            this.listenTo(this.collection, "update reset", this.render);
            this.listenTo(this.collection, "update reset", this.resetSelected);
        },

        events: {
            'change .truck-selector': 'truckSelected',
        },

        resetSelected: function () {
            if (!this.collection.findWhere({ id: +this.order.get('selectedTruckId') })) {
                console.log("Reset selectedTruckId from TruckListView, because collection changed/reseted");
                this.order.set('selectedTruckId', null);
            }
        },

        truckSelected: function () {
            var id = this.$el.find("select").val();
            this.order.set('selectedTruckId', id == "" ? null : +id);
        },

        render: function () {
            var $select = this.$el.find("select");

            $select.html("");
            $select.prop("disabled", this.collection.length == 0);
            if (this.collection.length == 0) {
                $select.append("<option>No available trucks</option>");
            } else {
                $select.append("<option value=''>No truck selected</option>");
                this.collection.forEach(function (truck) {
                    $select.append(this.template(truck.attributes));
                }, this)
                console.log("Selected truck id=" + this.order.get('selectedTruckId'));
                $select.val(this.order.get('selectedTruckId'));
            }
            this.$el.selectpicker('refresh')
        }
    });

    var DriverListView = Backbone.View.extend({
        model: App.TruckCollection,
        el: $(".driver-selector"),
        template: Handlebars.compile($("#driver-select-template").html()),

        initialize: function (options) {
            this.order = options.order;

            // trucksCollection is needed just to get order.maxDrivers
            this.trucksCollection = options.trucksCollection;
            this.listenTo(this.collection, "update reset", this.render);
            // this.listenTo(this.order, "change:selectedTruckId", this.resetSelected);
        },

        events: {
            'change .driver-selector': 'driverSelected',
        },

        resetSelected: function () {
            // this.order.set('selectedTruckId', null);
        },

        driverSelected: function () {
            var drivers = this.$el.find("select").val();
            if (typeof drivers !== 'object') {
                drivers = new Array(drivers);
            }

            var selectedDrivers = _.map(drivers, function (id) {
                return this.collection.findWhere({ id: +id })
            }, this);


             this.order.get('selectedDriversCollection').set(selectedDrivers);
        },

        render: function () {
            var $select = this.$el.find("select");

            // set max variants to choose
            if (this.order.get('selectedTruck') != null) {
                $select.attr("data-max-options", this.order.getMaxDrivers(this.trucksCollection));
            }

            $select.html("");
            if (this.collection.length == 0) {
                $select.append("<option>No available drivers</option>");
                $select.prop("disabled", true);
            } else {
                $select.prop("disabled", false);
                this.collection.forEach(function (driver) {
                    $select.append(this.template(driver.attributes));
                }, this)
            }
            this.$el.selectpicker('refresh')
        }
    });

    App.OrderController = function (options) {
        options || (options = {});
        this.initialize.apply(this, [ options ]);
    };

    _.extend(App.OrderController.prototype, Backbone.Events, {

        initialize: function (options) {

            this.order = new App.Order({
                cargoCollection: new App.CargoCollection(),
                driverCollection: new App.DriverCollection(),
                selectedDriversCollection: new App.DriverCollection(),
            });

            this.cityOrderCollection = new App.CityCollection();
            this.trucksCollection = new App.TruckCollection();
            this.driversCollection = new App.DriverCollection();

            // refresh cities when something changes in cargoes
            this.listenTo(this.order.get('cargoCollection'), [
                    'update', 'reset',
                    'change:srcCityName',
                    'change:dstCityName'
                ].join(' '), this.refreshCitiesOrder);

            this.listenTo(this.order.get('cargoCollection'), [
                'update', 'reset',
                'change:weight',
                'change:srcCityName',
                'change:dstCityName'
            ].join(' '), this.refreshMetaAndGetDrivers);


            // this.listenTo(this.cargoCollection, 'update', this.cargoConfigChanged);
            // this.listenTo(this.cargoCollection, 'change:weight', this.cargoConfigChanged);

            //
            this.listenTo(this.order, 'change:selectedTruckId', this.updateDriversList)
        },

        refreshCitiesOrder: function () {
            console.log("refreshCitiesOrder", this.order.get('cargoCollection'));
            this.cityOrderCollection.updateCities(this.order.get('cargoCollection'));
        },

        refreshMetaAndGetDrivers: function () {
            console.log("refreshMetaAndGetDriver");
            var request = {
                cargoes: this.order.get('cargoCollection').map(function (cargo) {
                    return {
                        name: cargo.get('name'),
                        weight: cargo.get('weight'),
                        srcCityId: cargo.get('srcCityId'),
                        dstCityId: cargo.get('dstCityId')
                    }
                }),
                citiesOrder: this.cityOrderCollection.pluck('id')
            };

            $.ajax(CONTEXT_PATH + '/api/order/routeMeta.do', {
                context: this,
                data: { route: JSON.stringify(request) },
            }).done(function (json) {
                console.log("Route meta arrived");
                this.order.set('routeLength', json.length);
                this.order.set('requiredCapacity', json.requiredCapacity);
                this.trucksCollection.reset(json.trucks);
            }).fail(function (jqXhr, textStatus) {
                alert("Ajax error:", jqXhr);
            })
        },

        bootstrap: function () {
            if (window.bootstrap != null) {
                this.trucksCollection.reset(window.bootstrap.trucksCollection);
                this.order.get('cargoCollection').reset(window.bootstrap.cargoCollection);
                this.order.get('driverCollection').reset(window.bootstrap.driverCollection);
                this.order.get('selectedDriversCollection').reset(window.bootstrap.driversSelectedCollection);

                // wait till we will receive routeLenght and then display the form with selected driver
                // this.listenToOnce(this.order, 'change:routeLength', this.updateDriversList);

                // set truckId only when routeLenght is loaded
                this.listenToOnce(this.order, 'change:routeLength', function () {
                    this.order.set('selectedTruckId', window.bootstrap.selectedTruckId);
                });

                return true;
            }
            return false;
        },

        start: function () {
            // cargo table
            var viewCargoes = new CargoTableView({collection: this.order.get('cargoCollection')});
            // cities order
            var viewCities = new RouteCityOrderView({ collection: this.cityOrderCollection, cargoCollection: this.cargoCollection });
            // order length info
            var viewRouteSummary = new RouteSummaryView({ model: this.order });

            var viewTruckList = new TruckListView({ collection: this.trucksCollection, order: this.order });
            var viewDriverList = new DriverListView({ collection: this.driversCollection, order: this.order, trucksCollection: this.trucksCollection });

            this.bootstrap();
        },


        updateDriversList: function () {

            console.log("Try update drivers list")

            if (this.order.get('selectedTruckId') == null) {
                this.driversCollection.reset();
                console.log("Truck not selected. Reseting drivers list")
                return;
            }

            if (this.order.get('routeLength') != null && this.trucksCollection.length > 0) {

                console.log("Ajax request drivers...")

                // truck selected, load the drivers
                $.ajax(CONTEXT_PATH + "/api/order/drivers.do", {
                        context: this,
                        data: {
                            cityId: this.cityOrderCollection.first().get('id'),
                            routeLength: this.order.get('routeLength'),
                            maxDrivers: this.order.getMaxDrivers(this.trucksCollection),
                        }
                    })
                    .done(function (driverList) {
                        console.log("List of drivers arrived ", driverList);
                        this.driversCollection.reset(driverList);
                    })
                    .fail(function (jqXhr) {
                        alert("ajax error: " + jqXhr.errorText);
                    });
            }
        },

    });


    window.controller = new App.OrderController();
    window.controller.start();

});
