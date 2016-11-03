'use strict';

describe('Controller Tests', function() {

    describe('Com_fuel_consumption Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_fuel_consumption, MockCfdi;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_fuel_consumption = jasmine.createSpy('MockCom_fuel_consumption');
            MockCfdi = jasmine.createSpy('MockCfdi');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_fuel_consumption': MockCom_fuel_consumption,
                'Cfdi': MockCfdi
            };
            createController = function() {
                $injector.get('$controller')("Com_fuel_consumptionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_fuel_consumptionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
