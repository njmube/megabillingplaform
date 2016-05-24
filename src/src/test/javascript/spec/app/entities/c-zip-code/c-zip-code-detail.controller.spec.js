'use strict';

describe('Controller Tests', function() {

    describe('C_zip_code Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_zip_code, MockC_location;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_zip_code = jasmine.createSpy('MockC_zip_code');
            MockC_location = jasmine.createSpy('MockC_location');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_zip_code': MockC_zip_code,
                'C_location': MockC_location
            };
            createController = function() {
                $injector.get('$controller')("C_zip_codeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_zip_codeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
