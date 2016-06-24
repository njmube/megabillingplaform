'use strict';

describe('Controller Tests', function() {

    describe('Tax_retentions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTax_retentions, MockTax_types;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTax_retentions = jasmine.createSpy('MockTax_retentions');
            MockTax_types = jasmine.createSpy('MockTax_types');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Tax_retentions': MockTax_retentions,
                'Tax_types': MockTax_types
            };
            createController = function() {
                $injector.get('$controller')("Tax_retentionsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:tax_retentionsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
