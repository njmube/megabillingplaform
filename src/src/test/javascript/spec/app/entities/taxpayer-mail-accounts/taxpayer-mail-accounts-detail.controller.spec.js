'use strict';

describe('Controller Tests', function() {

    describe('Taxpayer_mail_accounts Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTaxpayer_mail_accounts, MockTaxpayer_account;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTaxpayer_mail_accounts = jasmine.createSpy('MockTaxpayer_mail_accounts');
            MockTaxpayer_account = jasmine.createSpy('MockTaxpayer_account');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Taxpayer_mail_accounts': MockTaxpayer_mail_accounts,
                'Taxpayer_account': MockTaxpayer_account
            };
            createController = function() {
                $injector.get('$controller')("Taxpayer_mail_accountsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:taxpayer_mail_accountsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
