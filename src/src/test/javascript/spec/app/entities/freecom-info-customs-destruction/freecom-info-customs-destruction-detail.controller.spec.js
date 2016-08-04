'use strict';

describe('Controller Tests', function() {

    describe('Freecom_info_customs_destruction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_info_customs_destruction, MockFreecom_destruction_certificate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_info_customs_destruction = jasmine.createSpy('MockFreecom_info_customs_destruction');
            MockFreecom_destruction_certificate = jasmine.createSpy('MockFreecom_destruction_certificate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_info_customs_destruction': MockFreecom_info_customs_destruction,
                'Freecom_destruction_certificate': MockFreecom_destruction_certificate
            };
            createController = function() {
                $injector.get('$controller')("Freecom_info_customs_destructionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_info_customs_destructionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
