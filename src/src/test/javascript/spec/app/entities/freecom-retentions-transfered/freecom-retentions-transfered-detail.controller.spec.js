'use strict';

describe('Controller Tests', function() {

    describe('Freecom_retentions_transfered Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_retentions_transfered, MockFreecom_local_taxes;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_retentions_transfered = jasmine.createSpy('MockFreecom_retentions_transfered');
            MockFreecom_local_taxes = jasmine.createSpy('MockFreecom_local_taxes');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_retentions_transfered': MockFreecom_retentions_transfered,
                'Freecom_local_taxes': MockFreecom_local_taxes
            };
            createController = function() {
                $injector.get('$controller')("Freecom_retentions_transferedDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_retentions_transferedUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
