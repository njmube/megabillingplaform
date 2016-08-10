'use strict';

describe('Controller Tests', function() {

    describe('Freecom_beneficiary Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_beneficiary;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_beneficiary = jasmine.createSpy('MockFreecom_beneficiary');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_beneficiary': MockFreecom_beneficiary
            };
            createController = function() {
                $injector.get('$controller')("Freecom_beneficiaryDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_beneficiaryUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
