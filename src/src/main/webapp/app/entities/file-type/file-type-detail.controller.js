(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('File_typeDetailController', File_typeDetailController);

    File_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'File_type', 'File'];

    function File_typeDetailController($scope, $rootScope, $stateParams, entity, File_type, File) {
        var vm = this;
        vm.file_type = entity;
        vm.load = function (id) {
            File_type.get({id: id}, function(result) {
                vm.file_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:file_typeUpdate', function(event, result) {
            vm.file_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
