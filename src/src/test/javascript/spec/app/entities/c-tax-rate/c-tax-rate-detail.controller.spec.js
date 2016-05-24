'use strict';

describe('Controller Tests', function() {

    describe('C_tax_rate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_tax_rate, MockTax_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_tax_rate = jasmine.createSpy('MockC_tax_rate');
            MockTax_concept = jasmine.createSpy('MockTax_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_tax_rate': MockC_tax_rate,
                'Tax_concept': MockTax_concept
            };
            createController = function() {
                $injector.get('$controller')("C_tax_rateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_tax_rateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
