'use strict';

describe('Controller Tests', function() {

    describe('C_location Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_location, MockC_municipality, MockC_zip_code;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_location = jasmine.createSpy('MockC_location');
            MockC_municipality = jasmine.createSpy('MockC_municipality');
            MockC_zip_code = jasmine.createSpy('MockC_zip_code');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_location': MockC_location,
                'C_municipality': MockC_municipality,
                'C_zip_code': MockC_zip_code
            };
            createController = function() {
                $injector.get('$controller')("C_locationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_locationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
