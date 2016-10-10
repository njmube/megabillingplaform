'use strict';

describe('Controller Tests', function() {

    describe('Tax_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTax_concept, MockConcept, MockTax_types;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTax_concept = jasmine.createSpy('MockTax_concept');
            MockConcept = jasmine.createSpy('MockConcept');
            MockTax_types = jasmine.createSpy('MockTax_types');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Tax_concept': MockTax_concept,
                'Concept': MockConcept,
                'Tax_types': MockTax_types
            };
            createController = function() {
                $injector.get('$controller')("Tax_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:tax_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
