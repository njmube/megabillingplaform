(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataenajenantecopscDetailController', Com_dataenajenantecopscDetailController);

    Com_dataenajenantecopscDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Com_dataenajenantecopsc', 'Com_data_enajenante'];

    function Com_dataenajenantecopscDetailController($scope, $rootScope, $stateParams, entity, Com_dataenajenantecopsc, Com_data_enajenante) {
        var vm = this;
        vm.com_dataenajenantecopsc = entity;
        vm.load = function (id) {
            Com_dataenajenantecopsc.get({id: id}, function(result) {
                vm.com_dataenajenantecopsc = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:com_dataenajenantecopscUpdate', function(event, result) {
            vm.com_dataenajenantecopsc = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
