'use strict';

describe('Controller Tests', function() {

    describe('Tax_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTax_concept, MockC_tax_rate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTax_concept = jasmine.createSpy('MockTax_concept');
            MockC_tax_rate = jasmine.createSpy('MockC_tax_rate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Tax_concept': MockTax_concept,
                'C_tax_rate': MockC_tax_rate
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
