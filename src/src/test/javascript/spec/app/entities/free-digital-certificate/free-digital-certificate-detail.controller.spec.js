'use strict';

describe('Controller Tests', function() {

    describe('Free_digital_certificate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFree_digital_certificate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFree_digital_certificate = jasmine.createSpy('MockFree_digital_certificate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Free_digital_certificate': MockFree_digital_certificate
            };
            createController = function() {
                $injector.get('$controller')("Free_digital_certificateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:free_digital_certificateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
