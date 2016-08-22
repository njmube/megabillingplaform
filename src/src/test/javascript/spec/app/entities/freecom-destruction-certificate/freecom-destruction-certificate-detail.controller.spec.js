'use strict';

describe('Controller Tests', function() {

    describe('Freecom_destruction_certificate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFreecom_destruction_certificate, MockFree_cfdi, MockC_type_series;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFreecom_destruction_certificate = jasmine.createSpy('MockFreecom_destruction_certificate');
            MockFree_cfdi = jasmine.createSpy('MockFree_cfdi');
            MockC_type_series = jasmine.createSpy('MockC_type_series');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Freecom_destruction_certificate': MockFreecom_destruction_certificate,
                'Free_cfdi': MockFree_cfdi,
                'C_type_series': MockC_type_series
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
