'use strict';

describe('Controller Tests', function() {

    describe('Taxpayer_client Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTaxpayer_client, MockClient_address, MockTaxpayer_account;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTaxpayer_client = jasmine.createSpy('MockTaxpayer_client');
            MockClient_address = jasmine.createSpy('MockClient_address');
            MockTaxpayer_account = jasmine.createSpy('MockTaxpayer_account');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Taxpayer_client': MockTaxpayer_client,
                'Client_address': MockClient_address,
                'Taxpayer_account': MockTaxpayer_account
            };
            createController = function() {
                $injector.get('$controller')("Taxpayer_clientDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:taxpayer_clientUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
