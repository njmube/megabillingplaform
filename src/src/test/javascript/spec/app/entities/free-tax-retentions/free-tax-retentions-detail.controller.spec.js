'use strict';

describe('Controller Tests', function() {

    describe('Free_tax_retentions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFree_tax_retentions, MockFree_concept, MockTax_types;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFree_tax_retentions = jasmine.createSpy('MockFree_tax_retentions');
            MockFree_concept = jasmine.createSpy('MockFree_concept');
            MockTax_types = jasmine.createSpy('MockTax_types');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Free_tax_retentions': MockFree_tax_retentions,
                'Free_concept': MockFree_concept,
                'Tax_types': MockTax_types
            };
            createController = function() {
                $injector.get('$controller')("Free_tax_retentionsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:free_tax_retentionsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
