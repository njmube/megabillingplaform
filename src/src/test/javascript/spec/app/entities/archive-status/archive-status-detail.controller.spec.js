'use strict';

describe('Controller Tests', function() {

    describe('Archive_status Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockArchive_status;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockArchive_status = jasmine.createSpy('MockArchive_status');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Archive_status': MockArchive_status
            };
            createController = function() {
                $injector.get('$controller')("Archive_statusDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:archive_statusUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
