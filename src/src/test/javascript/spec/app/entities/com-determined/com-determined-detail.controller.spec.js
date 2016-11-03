'use strict';

describe('Controller Tests', function() {

    describe('Com_determined Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCom_determined, MockCom_concept_fuel, MockFreecom_tax_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCom_determined = jasmine.createSpy('MockCom_determined');
            MockCom_concept_fuel = jasmine.createSpy('MockCom_concept_fuel');
            MockFreecom_tax_type = jasmine.createSpy('MockFreecom_tax_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Com_determined': MockCom_determined,
                'Com_concept_fuel': MockCom_concept_fuel,
                'Freecom_tax_type': MockFreecom_tax_type
            };
            createController = function() {
                $injector.get('$controller')("Com_determinedDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:com_determinedUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
