'use strict';

describe('Controller Tests', function() {

    describe('Freecom_commodity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_commodity, MockFreecom_foreign_trade, MockFreecom_tariff_fraction, MockFreecom_custom_unit, MockFreecom_specific_descriptions;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_commodity = jasmine.createSpy('MockFreecom_commodity');
            MockFreecom_foreign_trade = jasmine.createSpy('MockFreecom_foreign_trade');
            MockFreecom_tariff_fraction = jasmine.createSpy('MockFreecom_tariff_fraction');
            MockFreecom_custom_unit = jasmine.createSpy('MockFreecom_custom_unit');
            MockFreecom_specific_descriptions = jasmine.createSpy('MockFreecom_specific_descriptions');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_commodity': MockFreecom_commodity,
                'Freecom_foreign_trade': MockFreecom_foreign_trade,
                'Freecom_tariff_fraction': MockFreecom_tariff_fraction,
                'Freecom_custom_unit': MockFreecom_custom_unit,
                'Freecom_specific_descriptions': MockFreecom_specific_descriptions
            };
            createController = function() {
                $injector.get('$controller')("Freecom_commodityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_commodityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
