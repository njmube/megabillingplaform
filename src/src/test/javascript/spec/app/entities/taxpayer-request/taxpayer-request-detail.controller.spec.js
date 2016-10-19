'use strict';

describe('Controller Tests', function() {

    describe('Taxpayer_request Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTaxpayer_request;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTaxpayer_request = jasmine.createSpy('MockTaxpayer_request');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Taxpayer_request': MockTaxpayer_request
            };
            createController = function() {
                $injector.get('$controller')("Taxpayer_requestDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:taxpayer_requestUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
