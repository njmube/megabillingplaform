'use strict';

describe('Controller Tests', function() {

    describe('Freecom_destruction_certificate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_destruction_certificate, MockC_class;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_destruction_certificate = jasmine.createSpy('MockFreecom_destruction_certificate');
            MockC_class = jasmine.createSpy('MockC_class');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_destruction_certificate': MockFreecom_destruction_certificate,
                'C_class': MockC_class
            };
            createController = function() {
                $injector.get('$controller')("Freecom_destruction_certificateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:freecom_destruction_certificateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
