'use strict';

describe('Controller Tests', function() {

    describe('C_tar Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockC_tar;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockC_tar = jasmine.createSpy('MockC_tar');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'C_tar': MockC_tar
            };
            createController = function() {
                $injector.get('$controller')("C_tarDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:c_tarUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
