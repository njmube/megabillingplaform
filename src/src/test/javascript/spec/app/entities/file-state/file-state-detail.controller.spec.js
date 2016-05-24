'use strict';

describe('Controller Tests', function() {

    describe('File_state Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFile_state, MockFile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFile_state = jasmine.createSpy('MockFile_state');
            MockFile = jasmine.createSpy('MockFile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'File_state': MockFile_state,
                'File': MockFile
            };
            createController = function() {
                $injector.get('$controller')("File_stateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:file_stateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
