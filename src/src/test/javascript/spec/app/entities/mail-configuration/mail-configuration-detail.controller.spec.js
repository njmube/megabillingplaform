'use strict';

describe('Controller Tests', function() {

    describe('Mail_configuration Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMail_configuration;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMail_configuration = jasmine.createSpy('MockMail_configuration');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Mail_configuration': MockMail_configuration
            };
            createController = function() {
                $injector.get('$controller')("Mail_configurationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'megabillingplatformApp:mail_configurationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
