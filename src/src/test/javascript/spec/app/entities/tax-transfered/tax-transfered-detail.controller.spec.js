'use strict';

describe('Controller Tests', function() {

    describe('Tax_transfered Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTax_transfered, MockTax_types, MockRate_type;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTax_transfered = jasmine.createSpy('MockTax_transfered');
            MockTax_types = jasmine.createSpy('MockTax_types');
            MockRate_type = jasmine.createSpy('MockRate_type');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Tax_transfered': MockTax_transfered,
                'Tax_types': MockTax_types,
                'Rate_type': MockRate_type
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
