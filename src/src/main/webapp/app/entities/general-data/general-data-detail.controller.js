(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('General_dataDetailController', General_dataDetailController);

    General_dataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'General_data'];

    function General_dataDetailController($scope, $rootScope, $stateParams, DataUtils, entity, General_data) {
        var vm = this;
        vm.general_data = entity;
        vm.load = function (id) {
            General_data.get({id: id}, function(result) {
                vm.general_data = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:general_dataUpdate', function(event, result) {
            vm.general_data = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
    }
})();
