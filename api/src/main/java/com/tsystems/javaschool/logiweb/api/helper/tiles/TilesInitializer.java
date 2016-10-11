/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.helper.tiles;

import org.apache.tiles.factory.AbstractTilesContainerFactory;
import org.apache.tiles.startup.AbstractTilesInitializer;
import org.apache.tiles.request.ApplicationContext;

public class TilesInitializer extends AbstractTilesInitializer {

    public TilesInitializer() {}

    @Override
    protected AbstractTilesContainerFactory createContainerFactory(ApplicationContext context) {
        return new TilesContainerFactory();
    }
}