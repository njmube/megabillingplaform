(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Archive_statusDetailController', Archive_statusDetailController);

    Archive_statusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Archive_status'];

    function Archive_statusDetailController($scope, $rootScope, $stateParams, entity, Archive_status) {
        var vm = this;
        vm.archive_status = entity;
        vm.load = function (id) {
            Archive_status.get({id: id}, function(result) {
                vm.archive_status = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:archive_statusUpdate', function(event, result) {
            vm.archive_status = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
