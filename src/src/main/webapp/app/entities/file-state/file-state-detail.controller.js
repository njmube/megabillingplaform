(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('File_stateDetailController', File_stateDetailController);

    File_stateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'File_state', 'File'];

    function File_stateDetailController($scope, $rootScope, $stateParams, entity, File_state, File) {
        var vm = this;
        vm.file_state = entity;
        vm.load = function (id) {
            File_state.get({id: id}, function(result) {
                vm.file_state = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:file_stateUpdate', function(event, result) {
            vm.file_state = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
