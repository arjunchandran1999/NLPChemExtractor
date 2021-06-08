#!/bin/bash
# Job name:
#SBATCH --job-name=hyp_search_6_7
#
# Account:
#SBATCH --account=fc_igemcomp
#
# Partition: - run on CPU only with `savio`
#SBATCH --partition=savio
#
# Wall clock limit:
#SBATCH --time=00:30:00

# Number of tasks needed for use case (example):
#SBATCH --ntasks=8
#
# Processors per task:
#SBATCH --cpus-per-task=1
#Number of GPUs, this can be in the format of "gpu:[1-4]", or "gpu:K80:[1-4] with the type included


## Command(s) to run:
source activate /global/home/groups/fc_igemcomp/software/scibert_env_NER
python dropout_hyp_search.py

