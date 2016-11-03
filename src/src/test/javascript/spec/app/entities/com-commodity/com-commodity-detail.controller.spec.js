'use strict';

describe('Controller Tests', function() {

    describe('Com_commodity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_commodity, MockCom_foreign_trade, MockCom_tariff_fraction, MockCom_custom_unit;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_commodity = jasmine.createSpy('MockCom_commodity');
            MockCom_foreign_trade = jasmine.createSpy('MockCom_foreign_trade');
            MockCom_tariff_fraction = jasmine.createSpy('MockCom_tariff_fraction');
            MockCom_custom_unit = jasmine.createSpy('MockCom_custom_unit');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_commodity': MockCom_commodity,
                'Com_foreign_trade': MockCom_foreign_trade,
                'Com_tariff_fraction': MockCom_tariff_fraction,
                'Com_custom_unit': MockCom_custom_unit
            };
            createController = function() {
                $injector.get('$controller')("Com_commodityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_commodityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
