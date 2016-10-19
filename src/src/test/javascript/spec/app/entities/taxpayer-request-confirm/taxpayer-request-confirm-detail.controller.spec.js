'use strict';

describe('Controller Tests', function() {

    describe('Taxpayer_request_confirm Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTaxpayer_request_confirm;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTaxpayer_request_confirm = jasmine.createSpy('MockTaxpayer_request_confirm');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Taxpayer_request_confirm': MockTaxpayer_request_confirm
            };
            createController = function() {
                $injector.get('$controller')("Taxpayer_request_confirmDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:taxpayer_request_confirmUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
