'use strict';

describe('Controller Tests', function() {

    describe('Digital_certificate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDigital_certificate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDigital_certificate = jasmine.createSpy('MockDigital_certificate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Digital_certificate': MockDigital_certificate
            };
            createController = function() {
                $injector.get('$controller')("Digital_certificateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:digital_certificateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
