'use strict';

describe('Controller Tests', function() {

    describe('Transactions_history Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTransactions_history, MockC_system, MockUser, MockType_transaction, MockTaxpayer_account;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTransactions_history = jasmine.createSpy('MockTransactions_history');
            MockC_system = jasmine.createSpy('MockC_system');
            MockUser = jasmine.createSpy('MockUser');
            MockType_transaction = jasmine.createSpy('MockType_transaction');
            MockTaxpayer_account = jasmine.createSpy('MockTaxpayer_account');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Transactions_history': MockTransactions_history,
                'C_system': MockC_system,
                'User': MockUser,
                'Type_transaction': MockType_transaction,
                'Taxpayer_account': MockTaxpayer_account
            };
            createController = function() {
                $injector.get('$controller')("Transactions_historyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:transactions_historyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
