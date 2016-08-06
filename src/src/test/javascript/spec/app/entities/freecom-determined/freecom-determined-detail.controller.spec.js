'use strict';

describe('Controller Tests', function() {

    describe('Freecom_determined Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_determined, MockFreecom_concept_fuel, MockFreecom_tax_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_determined = jasmine.createSpy('MockFreecom_determined');
            MockFreecom_concept_fuel = jasmine.createSpy('MockFreecom_concept_fuel');
            MockFreecom_tax_type = jasmine.createSpy('MockFreecom_tax_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_determined': MockFreecom_determined,
                'Freecom_concept_fuel': MockFreecom_concept_fuel,
                'Freecom_tax_type': MockFreecom_tax_type
            };
            createController = function() {
                $injector.get('$controller')("Freecom_determinedDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_determinedUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
