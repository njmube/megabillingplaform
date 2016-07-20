(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Audit_event_typeDetailController', Audit_event_typeDetailController);

    Audit_event_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Audit_event_type'];

    function Audit_event_typeDetailController($scope, $rootScope, $stateParams, entity, Audit_event_type) {
        var vm = this;
        vm.audit_event_type = entity;
        vm.load = function (id) {
            Audit_event_type.get({id: id}, function(result) {
                vm.audit_event_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:audit_event_typeUpdate', function(event, result) {
            vm.audit_event_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
