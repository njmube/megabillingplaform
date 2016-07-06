'use strict';

describe('Controller Tests', function() {

    describe('Tax_transfered Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTax_transfered, MockTax_types, MockFree_concept;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTax_transfered = jasmine.createSpy('MockTax_transfered');
            MockTax_types = jasmine.createSpy('MockTax_types');
            MockFree_concept = jasmine.createSpy('MockFree_concept');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Tax_transfered': MockTax_transfered,
                'Tax_types': MockTax_types,
                'Free_concept': MockFree_concept
            };
            createController = function() {
                $injector.get('$controller')("Tax_transferedDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:tax_transferedUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
