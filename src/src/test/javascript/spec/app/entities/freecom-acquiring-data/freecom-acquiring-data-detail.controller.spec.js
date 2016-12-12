'use strict';

describe('Controller Tests', function() {

    describe('Freecom_acquiring_data Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_acquiring_data, MockFreecom_public_notaries;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_acquiring_data = jasmine.createSpy('MockFreecom_acquiring_data');
            MockFreecom_public_notaries = jasmine.createSpy('MockFreecom_public_notaries');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_acquiring_data': MockFreecom_acquiring_data,
                'Freecom_public_notaries': MockFreecom_public_notaries
            };
            createController = function() {
                $injector.get('$controller')("Freecom_acquiring_dataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_acquiring_dataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
