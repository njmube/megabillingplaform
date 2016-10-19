'use strict';

describe('Controller Tests', function() {

    describe('Tax_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTax_concept, MockTax_types, MockTaxpayer_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTax_concept = jasmine.createSpy('MockTax_concept');
            MockTax_types = jasmine.createSpy('MockTax_types');
            MockTaxpayer_concept = jasmine.createSpy('MockTaxpayer_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Tax_concept': MockTax_concept,
                'Tax_types': MockTax_types,
                'Taxpayer_concept': MockTaxpayer_concept
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
