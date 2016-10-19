'use strict';

describe('Controller Tests', function() {

    describe('Discount_concept Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDiscount_concept, MockTaxpayer_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDiscount_concept = jasmine.createSpy('MockDiscount_concept');
            MockTaxpayer_concept = jasmine.createSpy('MockTaxpayer_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Discount_concept': MockDiscount_concept,
                'Taxpayer_concept': MockTaxpayer_concept
            };
            createController = function() {
                $injector.get('$controller')("Discount_conceptDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:discount_conceptUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
