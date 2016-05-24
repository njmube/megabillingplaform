'use strict';

describe('Controller Tests', function() {

    describe('Billing_account_state Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBilling_account_state;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBilling_account_state = jasmine.createSpy('MockBilling_account_state');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Billing_account_state': MockBilling_account_state
            };
            createController = function() {
                $injector.get('$controller')("Billing_account_stateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:billing_account_stateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
