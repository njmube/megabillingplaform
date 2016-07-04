'use strict';

describe('Controller Tests', function() {

    describe('Config_pathrootfile Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockConfig_pathrootfile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockConfig_pathrootfile = jasmine.createSpy('MockConfig_pathrootfile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Config_pathrootfile': MockConfig_pathrootfile
            };
            createController = function() {
                $injector.get('$controller')("Config_pathrootfileDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:config_pathrootfileUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
