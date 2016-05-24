'use strict';

describe('Controller Tests', function() {

    describe('Package_state Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPackage_state, MockPackage_transactions;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPackage_state = jasmine.createSpy('MockPackage_state');
            MockPackage_transactions = jasmine.createSpy('MockPackage_transactions');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Package_state': MockPackage_state,
                'Package_transactions': MockPackage_transactions
            };
            createController = function() {
                $injector.get('$controller')("Package_stateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:package_stateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
