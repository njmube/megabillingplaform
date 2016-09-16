'use strict';

describe('Controller Tests', function() {

    describe('Taxpayer_ftp_account Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTaxpayer_ftp_account, MockTaxpayer_account;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTaxpayer_ftp_account = jasmine.createSpy('MockTaxpayer_ftp_account');
            MockTaxpayer_account = jasmine.createSpy('MockTaxpayer_account');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Taxpayer_ftp_account': MockTaxpayer_ftp_account,
                'Taxpayer_account': MockTaxpayer_account
            };
            createController = function() {
                $injector.get('$controller')("Taxpayer_ftp_accountDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:taxpayer_ftp_accountUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
