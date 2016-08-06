'use strict';

describe('Controller Tests', function() {

    describe('Freecom_concept_fuel Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_concept_fuel, MockFreecom_fuel_consumption;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_concept_fuel = jasmine.createSpy('MockFreecom_concept_fuel');
            MockFreecom_fuel_consumption = jasmine.createSpy('MockFreecom_fuel_consumption');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_concept_fuel': MockFreecom_concept_fuel,
                'Freecom_fuel_consumption': MockFreecom_fuel_consumption
            };
            createController = function() {
                $injector.get('$controller')("Freecom_concept_fuelDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_concept_fuelUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
