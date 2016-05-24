'use strict';

describe('Controller Tests', function() {

    describe('Package_transactions Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPackage_transactions, MockPackage_state;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPackage_transactions = jasmine.createSpy('MockPackage_transactions');
            MockPackage_state = jasmine.createSpy('MockPackage_state');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Package_transactions': MockPackage_transactions,
                'Package_state': MockPackage_state
            };
            createController = function() {
                $injector.get('$controller')("Package_transactionsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:package_transactionsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
