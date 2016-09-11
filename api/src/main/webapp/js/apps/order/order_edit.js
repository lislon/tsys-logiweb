"use strict"
var app = {};

$(function () {

    var template = $('#hidden-template').html();

    // var $modal = $('#modal').modal({show: false});

    var Cargo = Backbone.Model.extend({
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

    var City = Backbone.Model.extend({
        defaults: {
            name: "",
            ordinal: 0,
        }
    });

    var RouteMeta = Backbone.Model.extend({
        defaults: {
            cargoes: {},
            cities: {},
            length: 0,
            duration: 0,
            requiredCapacity: 0,
        },
        initialize: function (attributes, options) {
            this.cargoes = options.cargoes;
            this.cities = options.cities;
        },

        sync: function(method, model, options) {
            if (method == "read") {

                $.ajax(CONTEXT_PATH + '/api/order/routeMeta.do', {
                    context: this,
                    data: this.formatGetRequest(),
                }).done(function (json) {
                    this.set('length', json.length);
                    this.set('duration', json.duration);
                }).fail(function (jqXhr, textStatus) {
                    alert("Ajax error:" + jqXhr.url);
                })
            }
        },
        formatGetRequest: function () {
            var request = {
                cargoes: this.cargoes.map(function (cargo) {
                    return {
                        name: cargo.get('name'),
                        weight: cargo.get('weight'),
                        srcCityId: cargo.get('srcCityId'),
                        dstCityId: cargo.get('dstCityId')
                    }
                }),
                citiesOrder: this.cities.map(function (city) {
                    return city.get('id');
                })
            };
            return { route: JSON.stringify(request) };
        }
    });

    var Truck = Backbone.Model.extend({
        defaults: {
            id: "", name: "", seats: 0
        },
    });

    var Trucks = Backbone.Collection.extend({
        model: Truck,
        url: function () {
            return CONTEXT_PATH + '/api/order/trucks.do'
        },
    });


    var CityCollection = Backbone.Collection.extend({
        model: City,

        events: {
            update: "onUpdate"
        },

        onUpdate: function (options) {
            // _.options.changes.added
            // city.set('ordinal', _.max([0, _.max(this.collection.pluck('ordinal'))]) + 1);
        },

        initialize: function (collection, options) {
            this.cargoes = options.cargoes;
            _.bind(this.canBeFirstInRoute, this);
            _.bind(this.canBeLastInRoute, this);
        },

        comparator: function(model) {
            return model.get('ordinal');
        },
        // Check that given city can be start of route
        canBeFirstInRoute: function (city) {
            return this.cargoes.findWhere({ srcCityId: city.get('id') }) != null;
        },
        canBeLastInRoute: function (city) {
            return this.cargoes.findWhere({ dstCityId: city.get('id') }) != null;
        }

    });


    var CargoCollection = Backbone.Collection.extend({
        model: Cargo
    });




    //
    // TABLE ROW
    //
    var CargoRowView = Backbone.View.extend({

        tagName: "tr",
        template: Handlebars.compile($('#cargo-item-template').html()),
        model: Cargo,
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
        model: CargoCollection,
        el: $("#cargoTable tbody"),

        initialize: function () {
            this.listenTo(this.collection, "add", this.render);

            var that = this;
            $(".addCargo").click(function () {
                var newCargo = new Cargo({title: 'default title'});
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
                var rowView = new CargoRowView({ model: this.collection.at(i) });
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
        model: Cargo,
        tagName: "div",

        template: Handlebars.compile($('#edit-cargo-template').html()),
        events: {
            // 'submit': "editCargoSubmit",
        },

        initialize: function () {
            this.$el.html(this.template(this.model.attributes));
            this.$modal = this.$el.find('#modal');
            var that = this;
            this.$modal.on("hidden.bs.modal", function () {
                that.remove();
            });
            this.$modal.on('shown.bs.modal', function () {
                $('#cargoName').focus()
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
        model: City,
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
        model: CityCollection,
        el: $(".route-summary"),
        template: Handlebars.compile($("#route-summary-template").html()),

        initialize: function () {
            this.listenTo(this.model, "change", this.render);
            // this.listenTo(this.collection, "remove", this.render);
        },

        render: function () {
            this.$el.html(this.template(this.model.attributes));
            return this;
        },
    });

    var RouteCityOrderView = Backbone.View.extend({
        model: CityCollection,
        el: $(".route-cities"),

        events: {
            'update-sort': 'updateSort',
        },


        initialize: function () {
            this.listenTo(this.collection, "add", this.render);
            this.listenTo(this.collection, "remove", this.render);
            var that = this;
            this.$el.sortable({
                // Only make the .panel-heading child elements support dragging.
                // Omit this to make then entire <li>...</li> draggable.
                // handle: '.panel-heading',
                stop: function( event, ui ) {
                    ui.item.trigger('update', ui.item.index());
                },

            });
        },

        isNewSortValid: function (model, newPosition) {

            // case 1: newModel position is on edge
            if (newPosition == 0) {
                return this.collection.canBeFirstInRoute(model);
            }
            if (newPosition == this.collection.length - 1) {
                return this.collection.canBeLastInRoute(model);
            }

            // case 2: newModel position is in middle. So other elements now can be on edge positions.
            var newFirst = _.first(this.collection.without(model));
            if (!this.collection.canBeFirstInRoute(newFirst)) {
                return false;
            }

            var newLast = _.last(this.collection.without(model));
            if (!this.collection.canBeLastInRoute(newLast)) {
                return false;
            }

            return true;
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
                console.log("Not valid");
                return;
            }
            console.log("valid");

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
        model: Trucks,
        el: $(".truck-selector"),
        template: Handlebars.compile($("#truck-select-template").html()),

        initialize: function () {
            this.listenTo(this.collection, "update", this.render);
        },

        render: function () {
            console.log(this.collection);

            var $select = this.$el.find("select");

            $select.html("");
            if (this.collection.length == 0) {
                $select.append("<option>No available trucks</option>");
                $select.prop("disabled", true);
            } else {
                $select.prop("disabled", false);
                this.collection.forEach(function (truck) {
                    $select.append(this.template(truck.attributes));
                }, this)
            }
            this.$el.selectpicker('refresh')
        }
    });


    var MyController = function (options) {
        options || (options = {});
        this.cargoes = options.cargoes;
        this.seqCities = options.cities;
        this.initialize.apply(this, [ options ]);
    };

    _.extend(MyController.prototype, Backbone.Events, {

        cargoConfigChanged: function () {

            var oldSeqCities = this.seqCities.pluck('id');

            // Loop through cargoes. For each cargo check that it exists in seqCities array.
            // If cargo city is not found in seqCities array, append it to end of seqCities.
            _.forEach(['src', 'dst'], function (dir) {
                this.cargoes.forEach(function (cargo) {

                    var existingCity = this.seqCities.find({id: cargo.get(dir + 'CityId')});
                    // If city from cargo collection is not present in sequence seqCities
                    if (existingCity == null) {
                        // Add it to seqCities
                        var newSeqCity = new City({
                            name: cargo.get(dir + 'CityName'),
                            id: cargo.get(dir + 'CityId'),
                            ordinal: this.seqCities.length,
                        });
                        this.seqCities.add(newSeqCity);
                    } else {
                        // existing city
                        oldSeqCities = _.without(oldSeqCities, existingCity.get('id'));
                    }
                }, this);
            }, this);

            // Remove cities from seqCities that are not present in cargoes
            _.forEach(oldSeqCities, function (id) {
                this.seqCities.remove(this.seqCities.where({'id': id}))
            }, this);

        },

        initialize: function (options) {
            this.listenTo(this.cargoes, 'update', this.cargoConfigChanged);
            this.listenTo(this.cargoes, 'change:srcCityName', this.cargoConfigChanged);
            this.listenTo(this.cargoes, 'change:dstCityName', this.cargoConfigChanged);
        }
    });



    //
    // CITIES UL VIEW
    //

    // edit-cargo-template

    var cargo1 = new Cargo({
        name: "BV4891",
        title: "My new item",
        weight: 100,
        srcCityId: 1,
        srcCityName: "Berlin",
        dstCityId: 2,
        dstCityName: "Moscow",

    });

    var cargo2 = new Cargo({
        name: "BV4891",
        title: "My new item 2",
        weight: 100,
        srcCityId: 1,
        srcCityName: "Berlin",
        dstCityId: 3,
        dstCityName: "Saint-Petersburg",
    });

    app.cargoes = new CargoCollection([cargo1, cargo2]);
    app.cityCollection = new CityCollection(null, { cargoes: app.cargoes });
    app.routeMeta = new RouteMeta({}, { cargoes: app.cargoes, cities: app.cityCollection });
    app.trucks = new Trucks();


    var viewCargoes = new CargoTableView({collection: app.cargoes});
    var viewCities = new RouteCityOrderView({ collection: app.cityCollection });
    var viewRouteSummary = new RouteSummaryView({model: app.routeMeta });
    var viewTruckList = new TruckListView({ collection: app.trucks });
    // var view = new CargoRowView({model: cargo1});


    var controller = new MyController({
        cargoes: app.cargoes,
        cities: app.cityCollection,
    });
    controller.cargoConfigChanged();

    app.routeMeta.fetch();
    app.trucks.fetch();

    viewCargoes.render();
    viewRouteSummary.render();
    viewCities.render();

});
