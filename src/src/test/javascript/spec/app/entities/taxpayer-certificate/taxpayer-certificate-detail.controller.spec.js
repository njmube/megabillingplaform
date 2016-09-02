'use strict';

describe('Controller Tests', function() {

    describe('Taxpayer_certificate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTaxpayer_certificate;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTaxpayer_certificate = jasmine.createSpy('MockTaxpayer_certificate');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Taxpayer_certificate': MockTaxpayer_certificate
            };
            createController = function() {
                $injector.get('$controller')("Taxpayer_certificateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:taxpayer_certificateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
