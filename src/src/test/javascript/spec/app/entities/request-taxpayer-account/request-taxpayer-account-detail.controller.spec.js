'use strict';

describe('Controller Tests', function() {

    describe('Request_taxpayer_account Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockRequest_taxpayer_account, MockRequest_state, MockTax_address_request, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockRequest_taxpayer_account = jasmine.createSpy('MockRequest_taxpayer_account');
            MockRequest_state = jasmine.createSpy('MockRequest_state');
            MockTax_address_request = jasmine.createSpy('MockTax_address_request');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Request_taxpayer_account': MockRequest_taxpayer_account,
                'Request_state': MockRequest_state,
                'Tax_address_request': MockTax_address_request,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("Request_taxpayer_accountDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:request_taxpayer_accountUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
