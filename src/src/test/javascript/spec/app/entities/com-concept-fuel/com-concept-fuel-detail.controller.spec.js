'use strict';

describe('Controller Tests', function() {

    describe('Com_concept_fuel Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_concept_fuel, MockCom_fuel_consumption;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_concept_fuel = jasmine.createSpy('MockCom_concept_fuel');
            MockCom_fuel_consumption = jasmine.createSpy('MockCom_fuel_consumption');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_concept_fuel': MockCom_concept_fuel,
                'Com_fuel_consumption': MockCom_fuel_consumption
            };
            createController = function() {
                $injector.get('$controller')("Com_concept_fuelDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_concept_fuelUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
