'use strict';

describe('Controller Tests', function() {

    describe('Freecom_data_operation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_data_operation, MockFreecom_public_notaries;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_data_operation = jasmine.createSpy('MockFreecom_data_operation');
            MockFreecom_public_notaries = jasmine.createSpy('MockFreecom_public_notaries');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_data_operation': MockFreecom_data_operation,
                'Freecom_public_notaries': MockFreecom_public_notaries
            };
            createController = function() {
                $injector.get('$controller')("Freecom_data_operationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_data_operationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
