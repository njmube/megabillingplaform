'use strict';

describe('Controller Tests', function() {

    describe('Freecom_tfd Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_tfd;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_tfd = jasmine.createSpy('MockFreecom_tfd');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_tfd': MockFreecom_tfd
            };
            createController = function() {
                $injector.get('$controller')("Freecom_tfdDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_tfdUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
