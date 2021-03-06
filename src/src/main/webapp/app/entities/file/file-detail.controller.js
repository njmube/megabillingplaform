(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('FileDetailController', FileDetailController);

    FileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'File', 'File_state', 'File_type'];

    function FileDetailController($scope, $rootScope, $stateParams, entity, File, File_state, File_type) {
        var vm = this;
        vm.file = entity;
        
        var unsubscribe = $rootScope.$on('megabillingplatformApp:fileUpdate', function(event, result) {
            vm.file = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
