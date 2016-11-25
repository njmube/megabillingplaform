'use strict';

describe('Controller Tests', function() {

    describe('Client Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockClient, MockClient_address;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockClient = jasmine.createSpy('MockClient');
            MockClient_address = jasmine.createSpy('MockClient_address');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Client': MockClient,
                'Client_address': MockClient_address
            };
            createController = function() {
                $injector.get('$controller')("ClientDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:clientUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
