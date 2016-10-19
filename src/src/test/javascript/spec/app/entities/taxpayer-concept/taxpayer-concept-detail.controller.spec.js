'use strict';

describe('Controller Tests', function() {

    describe('Taxpayer_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTaxpayer_concept, MockTaxpayer_account, MockMeasure_unit_concept, MockPrice_concept, MockTax_concept, MockDiscount_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTaxpayer_concept = jasmine.createSpy('MockTaxpayer_concept');
            MockTaxpayer_account = jasmine.createSpy('MockTaxpayer_account');
            MockMeasure_unit_concept = jasmine.createSpy('MockMeasure_unit_concept');
            MockPrice_concept = jasmine.createSpy('MockPrice_concept');
            MockTax_concept = jasmine.createSpy('MockTax_concept');
            MockDiscount_concept = jasmine.createSpy('MockDiscount_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Taxpayer_concept': MockTaxpayer_concept,
                'Taxpayer_account': MockTaxpayer_account,
                'Measure_unit_concept': MockMeasure_unit_concept,
                'Price_concept': MockPrice_concept,
                'Tax_concept': MockTax_concept,
                'Discount_concept': MockDiscount_concept
            };
            createController = function() {
                $injector.get('$controller')("Taxpayer_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:taxpayer_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
